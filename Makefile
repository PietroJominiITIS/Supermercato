.PHONY: default clean

COMPILER = javac
RUNTIME = java

default:
	$(COMPILER) CLI.java

clean:
	rm *.class
