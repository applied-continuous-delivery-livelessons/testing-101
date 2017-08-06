# Testing 101
- spring-boot-starter-test (assertj, )
- style of naming tests: `methodUnderTestWhenFooIsNullShouldThrowException()`
- `@MockBean`
- BDD w/ mockito when( mock.foo() ) .willReturn()..
- @Rule
  pubic ExpectedException exception = ExpectedException.none();
  expectation.thrown( SomeException.class )
- @DataJpaTest && TestEntityManager
- `MockResstServiceServer` (make sure u use the `RestTemplateBuilder` to create a RrestTemplate)
- compare and contrast this weith WireMock
- @JsonTest and @Autowired JacksonTest<VehicleDetails> json
- Spring REST Docs
- spies vs mocks
- `WebMvcTest`
- how to setup integration tests in your maven build as a separate phase
- BDD - given / when / then  
- Selenium/WebDriver 
