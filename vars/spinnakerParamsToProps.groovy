#!/usr/bin/env groovy
/*
  Utility to take a set of parameters generated for spinnaker
  webhook / pubsub and flatten it to a properties file instead.
*/
def call(Map params, List<Map> artifacts) {
  def data = mapToList(params)

  artifacts.each { artifact ->
    if (artifact.name.contains("/values/")) {
      def key = artifact.name.split("/").last().replace(".yaml", "")
      data.push("values_${key}=${artifact.reference}")
    }
    if (artifact.name =~ /\S+?\d+\.\d+\.\d+(|\S+)\.tgz/) {
      data.push("chart_pkg=${artifact.reference}")
    }
  }

  return data
}
