.PHONY: default clean

COMPILER = javac
RUNTIME = java

default:
	$(COMPILER) Main.java

clean:
	rm *.class
