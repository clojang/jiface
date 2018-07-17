.PHONY: docs

ROOT_DIR = $(shell pwd)
DOCS_DIR = $(ROOT_DIR)/docs
CURRENT = $(DOCS_DIR)/current
ERL_DOCS_SRC = ~/Dropbox/Docs/Erlang/$(ERL_RELEASE_VERSION)
ERL_DOCS_DIR = $(CURRENT)/erlang
JAVA_DOCS_DIR = $(ERL_DOCS_DIR)/java
REMOTE_DOCS_HOST = http://clojang.github.io/jiface/current/java
LOCAL_DOCS_HOST = localhost
LOCAL_DOCS_PORT = 5099

devdocs:
	@echo "\nRunning docs server on http://$(LOCAL_DOCS_HOST):$(LOCAL_DOCS_PORT)..."
	@lein with-profile +docs simpleton $(LOCAL_DOCS_PORT) file :from $(CURRENT)

docs: clean-docs pre-docs clojang-docs java-docs erl-docs

pre-docs:
	@echo "\nBuilding docs ...\n"
	@lein clean

java-docs:
	javadoc -public \
	         -use \
	         -version \
	         -author \
	         -nodeprecated \
	         -keywords \
	         -quiet \
	         -d $(JAVA_DOCS_DIR) $(JINTERFACE_FILES)

erl-docs:
	@mkdir -p $(ERL_DOCS_DIR) $(DOCS_DIR)/doc
	cp \
	$(ERL_DOCS_SRC)/lib/jinterface-$(JINTERFACE_VER)/doc/html/users_guide.html \
	$(ERL_DOCS_SRC)/lib/jinterface-$(JINTERFACE_VER)/doc/html/jinterface_users_guide.html \
	$(ERL_DOCS_DIR)
	cp -r \
	$(ERL_DOCS_SRC)/doc/js \
	$(ERL_DOCS_SRC)/doc/otp_doc.css \
	$(ERL_DOCS_SRC)/doc/erlang-logo.png \
	$(CURRENT)

clojang-docs:
	@lein with-profile +docs codox

clean-docs:
	@rm -rf $(CURRENT)
