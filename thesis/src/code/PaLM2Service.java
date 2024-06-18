@Override
public List<StoryLine> createStoryLines(Story story) {
    try {
        PredictResponse predictResponse = vertexAI
            .getPredictionServiceClient()
            .predict(PredictRequest.newBuilder()
                .setEndpoint(
                    getEndpointName(story.getModelName().getName())
                    .toString())
                .addInstances(getPrompt(generatePrompt(story)))
                .setParameters(getParameters())
                .build());

        String json = predictResponse
            .getPredictions(0).getStructValue().getFieldsOrThrow("content")
            .getStringValue()
            .replaceFirst("```json\n", "")
            .replaceFirst("```", "");

        return objectMapper.readValue(json, new TypeReference<>() {});
    } catch (IOException e) {
        log.warn("Failed to generate story: {}", e.getMessage());
        throw new InternalServerErrorException("Failed to generate story");
    }
}
