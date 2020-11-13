package com.grabanotherbyte.jackson.mixins;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.grabanotherbyte.jackson.mixins.Person.PersonReversedDeserializer;
import com.grabanotherbyte.jackson.mixins.Person.PersonReversedSerializer;

@JsonSerialize(using = PersonReversedSerializer.class)
@JsonDeserialize(using = PersonReversedDeserializer.class)
public abstract class PersonMixin {}
