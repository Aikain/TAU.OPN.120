#!/bin/bash

cd "$(dirname "$0")/src"

pdflatex -output-directory=../out main.tex

cd ../out
makeindex -s main.ist -t main.glg -o main.gls main.glo
cd ../src

biber --output-directory ../out main
pdflatex -output-directory=../out main.tex
pdflatex -output-directory=../out main.tex

cd ..
