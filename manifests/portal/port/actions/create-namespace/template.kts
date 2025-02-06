import io.fabric8.kubernetes.api.model.ObjectReference
import io.platformspec.crd.provider.spec.Categories
import io.platformspec.crd.credential.Credential
import io.verticle.oss.platformapi.kinds.provider.api.cicd.Environment
import io.verticle.oss.platformapi.kinds.provider.api.secrets.Secret
import io.verticle.oss.platformapi.kinds.provider.spi.*
import io.verticle.oss.platformscript.api.*
import io.platformspec.crd.provider.Spec
import io.verticle.oss.platformapi.serviceprovider.Context
import java.nio.file.Paths
import java.nio.file.Path
import java.nio.file.Files
import io.verticle.oss.platformsdk.core.support.git.model.GitRepository
import io.verticle.oss.platformapi.kinds.provider.crd.repository.RepositoryProviderResource
import io.platformspec.crd.portal.Portal

fun executeAction(){

    log.info("[create-namespace] starting script")

    val templateDirPath = Paths.get("manifests", "portal", "port", "actions", "create-namespace")

    // map to Port fields
    val namespace = Resource.getSpec().getConfig().get("namespace").asText()

    log.info("[create-repository-github] rendering template with namespace name $namespace")

    val renderedTemplate: String = TemplateApi.renderTemplateAsString(
        mapOf("repositoryName" to repositoryName),
        templateDirPath,
        Context,
        "namespace.yaml.jte")

    println(renderedTemplate)


    // apply resource
    PlatformResourceAPI.apply(renderedTemplate, "platformspec")

}