package com.musinsa.muordi.contents.display.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ShowcaseRepository extends JpaRepository<Showcase, Integer> {
}
