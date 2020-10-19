package com.grabanotherbyte.jackson;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.grabanotherbyte.jackson.Person.PersonReversedDeserializer;
import com.grabanotherbyte.jackson.Person.PersonReversedSerializer;

@JsonSerialize(using = PersonReversedSerializer.class)
@JsonDeserialize(using = PersonReversedDeserializer.class)
public abstract class PersonMixin {}
