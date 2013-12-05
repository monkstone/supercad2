# Supercad2


Updating Guillaume LaBelles (Nembrini update) to processing 2.1 (I'm using jdk 7) and some refactoring to use enums, to store state, and in switch to select export type. The POVRAY2 version is now a lot more sophisticated, taking camera params from processing and translating them to povray, so now povray "scene" geometry more closely matches processing sketch. Also added capability of "live" povray tracing (just supply path to povray on your system and use my sketch template). Once the tracing has completed, the ray-traced image is displayed in the processing window.
