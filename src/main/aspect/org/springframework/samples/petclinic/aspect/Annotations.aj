package org.springframework.samples.petclinic.aspect;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.samples.petclinic.vet.VetRepository;

public aspect Annotations {
	declare @method : * VetRepository+.findAll() : @Cacheable("vets");
}