compile:
	@lein compile

local:
	@lein jar && lein install

local-standalone:
	@lein uberjar && install

debug:
	@echo $(PROJ)
	@echo $(ERL_LIBS)
	@echo $(JINTERFACE)
	@echo $(JINTERFACE_VER)
	@echo $(VERSION)
	@echo $(JAR)

clojars:
	@lein deploy clojars

repl:
	@lein with-profile +test repl
