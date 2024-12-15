@Override
public List<StoryLine> createStoryLines(Story story) {
    GenerativeModel model = new GenerativeModel(
            story.getModelName().getName(),
            vertexAI
    );
    ChatSession chatSession = model.startChat();
    chatSession.withGenerationConfig(createGenerationConfig());
    chatSessions.put(story.getId(), chatSession);

    try {
        GenerateContentResponse response =
            chatSession.sendMessage(createInitializeContent(story));
        String text = response
            .getCandidates(0)
            .getContent()
            .getParts(0)
            .getText();

        return objectMapper.readValue(text, new TypeReference<>() {});
    } catch (IOException e) {
        log.warn("Failed to generate story: {}", e.getMessage());
        throw new InternalServerErrorException(
            "Failed to generate story");
    }
}

@Override
public List<StoryLine> continueStoryLines(Story story) {
    if (!chatSessions.containsKey(story.getId()))
        throw new GoneException();

    ChatSession chatSession = chatSessions.get(story.getId());
    try {
        GenerateContentResponse response =
            chatSession.sendMessage("Jatka tarinaa");
        String text = response
            .getCandidates(0)
            .getContent()
            .getParts(0)
            .getText();

        return objectMapper.readValue(text, new TypeReference<>() {});
    } catch (IOException e) {
        log.warn(
            "Failed to generate story continuation: {}",
            e.getMessage()
        );
        throw new InternalServerErrorException(
            "Failed to generate story continuation"
        );
    }
}
