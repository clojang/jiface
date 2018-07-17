check:
	@lein ltest :unit

check-system:
	@lein ltest :system

check-integration:
	@lein ltest :integration

check-vers:
	@lein check-vers

check-all: check-vers
	@lein ltest :all
