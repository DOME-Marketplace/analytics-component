package com.dome.matomo_analytics.repository.matomo;

import com.dome.matomo_analytics.model.matomo.Referrers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReferrersRepository extends JpaRepository<Referrers, UUID> {
}
