check:
	@lein with-profile +test test :unit

check-system:
	@lein with-profile +test test :system

check-integration:
	@lein with-profile +test test :integration

check-deps:
	@lein with-profile +test ancient
	@lein with-profile +test ancient check-profiles

check-all: check-deps
	@lein with-profile +test test :all
