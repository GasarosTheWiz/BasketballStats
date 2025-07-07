package app.BasketballStats.repository;

import app.BasketballStats.model.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
}
