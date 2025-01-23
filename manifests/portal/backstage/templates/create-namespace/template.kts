import io.fabric8.kubernetes.api.model.ObjectReference
import io.platformspec.crd.provider.spec.Categories
import io.verticle.oss.platformapi.kinds.provider.api.cicd.Environment
import io.verticle.oss.platformapi.kinds.provider.spi.*
import io.verticle.oss.platformscript.api.*
import io.platformspec.crd.provider.Spec
import io.verticle.oss.platformapi.serviceprovider.Context
import java.nio.file.Paths

fun prepareTemplate(){

    log.info("starting script")

    val clusters = ClusterApi.getClusters()

    log.info("[X] TemplateApi available")

    log.info("rendering template ...")
    val renderedTemplate = TemplateApi.renderTemplateAsString(
        mapOf("clusters" to clusters),
        Paths.get("claims", "portal", "backstage", "templates", "create-namespace"),
        Context,
        "template.yaml.jte")
    
    println(renderedTemplate)

}