package com.srini.feature.config;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.srini.feature.newrelic.client.NewRelicClient;
import com.srini.feature.newrelic.dto.Deployment;

import org.ff4j.audit.Event;
import org.ff4j.audit.EventConstants;
import org.ff4j.audit.EventQueryDefinition;
import org.ff4j.audit.EventSeries;
import org.ff4j.audit.MutableHitCount;
import org.ff4j.audit.chart.BarChart;
import org.ff4j.audit.chart.PieChart;
import org.ff4j.audit.chart.TimeSeriesChart;
import org.ff4j.audit.repository.EventRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomEventRepository implements EventRepository {

  private final EventRepository source;
  private final NewRelicClient newRelicDeploymentsClient;

  @Override
  public boolean saveEvent(Event e) {
    boolean toggleSwitched = isToggleSwitched(e);
    if (!toggleSwitched) {
      return true;
    }
    createNewRelicDeployment(e);
    return source.saveEvent(e);
  }

  private void createNewRelicDeployment(Event e) {
    Deployment deployment = new Deployment();
    deployment.setRevision("-");
    deployment.setChangelog(String.format("Feature flag: %s, action: %s", e.getName(), e.getAction()));
    deployment.setUser("anonymous");
    newRelicDeploymentsClient.createDeployment(deployment);
  }

  private boolean isToggleSwitched(Event e) {
    return EventConstants.ACTION_TOGGLE_ON.equals(e.getAction())
        || EventConstants.ACTION_TOGGLE_OFF.equals(e.getAction());
  }

  @Override
  public Event getEventByUUID(String uuid, Long timestamp) {
    return source.getEventByUUID(uuid, timestamp);
  }

  @Override
  public Map<String, MutableHitCount> getFeatureUsageHitCount(EventQueryDefinition query) {
    return source.getFeatureUsageHitCount(query);
  }

  @Override
  public PieChart getFeatureUsagePieChart(EventQueryDefinition query) {
    return source.getFeatureUsagePieChart(query);
  }

  @Override
  public BarChart getFeatureUsageBarChart(EventQueryDefinition query) {
    return source.getFeatureUsageBarChart(query);
  }

  @Override
  public TimeSeriesChart getFeatureUsageHistory(EventQueryDefinition query, TimeUnit tu) {
    return source.getFeatureUsageHistory(query, tu);
  }

  @Override
  public int getFeatureUsageTotalHitCount(EventQueryDefinition query) {
    return source.getFeatureUsageTotalHitCount(query);
  }

  @Override
  public EventSeries searchFeatureUsageEvents(EventQueryDefinition query) {
    return source.searchFeatureUsageEvents(query);
  }

  @Override
  public void purgeFeatureUsage(EventQueryDefinition query) {
    source.purgeFeatureUsage(query);
  }

  @Override
  public Map<String, MutableHitCount> getHostHitCount(EventQueryDefinition query) {
    return source.getHostHitCount(query);
  }

  @Override
  public PieChart getHostPieChart(EventQueryDefinition query) {
    return source.getHostPieChart(query);
  }

  @Override
  public BarChart getHostBarChart(EventQueryDefinition query) {
    return source.getHostBarChart(query);
  }

  @Override
  public Map<String, MutableHitCount> getUserHitCount(EventQueryDefinition query) {
    return source.getUserHitCount(query);
  }

  @Override
  public PieChart getUserPieChart(EventQueryDefinition query) {
    return source.getUserPieChart(query);
  }

  @Override
  public BarChart getUserBarChart(EventQueryDefinition query) {
    return source.getUserBarChart(query);
  }

  @Override
  public Map<String, MutableHitCount> getSourceHitCount(EventQueryDefinition query) {
    return source.getSourceHitCount(query);
  }

  @Override
  public PieChart getSourcePieChart(EventQueryDefinition query) {
    return source.getSourcePieChart(query);
  }

  @Override
  public BarChart getSourceBarChart(EventQueryDefinition query) {
    return source.getSourceBarChart(query);
  }

  @Override
  public EventSeries getAuditTrail(EventQueryDefinition query) {
    return source.getAuditTrail(query);
  }

  @Override
  public void purgeAuditTrail(EventQueryDefinition query) {
    source.purgeAuditTrail(query);
  }

  @Override
  public void createSchema() {
    source.createSchema();
  }

}