package com.kosmas.springit.repository;

import com.kosmas.springit.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {
  //  Link findOne(String id);
    //Link findById();



}
