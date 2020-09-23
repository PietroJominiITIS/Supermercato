.PHONY: default

COMPILER = javac
RUNTIME = java

default:
	@$(COMPILER) Main.java
	@$(RUNTIME) Main
