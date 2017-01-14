check:
	@lein with-profile +test test :unit

check-system:
	@lein with-profile +test test :system

check-integration:
	@lein with-profile +test test :integration

check-all:
	@lein with-profile +test test :all
