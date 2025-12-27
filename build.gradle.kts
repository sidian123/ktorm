group = "live.sidian"
version = file("ktorm.version").readLines()[0]

plugins {
    id("ktorm.dokka")
    id("com.gradleup.nmcp.aggregation") version "1.2.0"
}

nmcpAggregation {
    centralPortal {
        username = project.findProperty("SONATYPE_USERNAME") as String?
        password = project.findProperty("SONATYPE_PASSWORD") as String?
        publishingType = "USER_MANAGED"
    }
    publishAllProjectsProbablyBreakingProjectIsolation()
}