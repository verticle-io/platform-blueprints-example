import io.fabric8.kubernetes.api.model.ObjectReference
import io.platformspec.crd.provider.spec.Categories
import io.verticle.oss.platformapi.kinds.provider.api.cicd.Environment
import io.verticle.oss.platformapi.kinds.provider.spi.*
import io.verticle.oss.platformscript.api.*
import io.platformspec.crd.provider.Spec
import io.verticle.oss.platformapi.kinds.provider.api.Context
import java.nio.file.Path

fun prepareTemplate(){

    log.info("starting script")

    val clusters = ClusterApi.getClusters()

    assert(TemplateApi != null)
    log.info("[X] TemplateApi available")

    val renderedTemplate = TemplateApi.renderTemplateAsString(
            mapOf("clusters" to clusters),
            Path.of("portal", "backstage", "templates", "create-namespace")
            "template.yaml.kte", )

    println(renderedTemplate)

}