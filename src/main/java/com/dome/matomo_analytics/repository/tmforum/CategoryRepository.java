package com.dome.matomo_analytics.repository.tmforum;

import com.dome.matomo_analytics.model.tmforum.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
