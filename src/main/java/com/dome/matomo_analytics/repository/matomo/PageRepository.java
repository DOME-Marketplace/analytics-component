package com.dome.matomo_analytics.repository.matomo;

import com.dome.matomo_analytics.model.matomo.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PageRepository extends JpaRepository<Page, UUID> {
    Optional<Page> findByLabel(String label);
}
