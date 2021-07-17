#### Internationalization


##### Configuration

- LocaleResolver
	- Default Locale - Locale.US
- ResourceBandleMessageSource

##### Usage
-  Autowire MessageSource
- @RequestHeader(value = "Accept-Language", required = false) Locale locale
- messageSource.getMessage("HelloWorld.message", null, locale)