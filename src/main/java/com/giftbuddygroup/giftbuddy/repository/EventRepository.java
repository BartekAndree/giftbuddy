package com.giftbuddygroup.giftbuddy.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giftbuddygroup.giftbuddy.model.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {}
