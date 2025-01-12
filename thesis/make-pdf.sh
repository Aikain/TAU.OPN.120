#!/bin/bash

cd "$(dirname "$0")/src"

# https://tex.stackexchange.com/a/52994
export max_print_line=1000
export error_line=254
export half_error_line=238

pdflatex -output-directory=../out main.tex

cd ../out
makeindex -s main.ist -t main.glg -o main.gls main.glo
cd ../src

biber --output-directory ../out main
pdflatex -output-directory=../out main.tex
pdflatex -output-directory=../out main.tex

cd ..
