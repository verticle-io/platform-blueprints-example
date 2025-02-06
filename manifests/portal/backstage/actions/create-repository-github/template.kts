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

    log.info("[create-repository-github] starting script")

    val templateDirPath = Paths.get("manifests", "portal", "backstage", "actions", "create-repository-github")

    // map to Port fields
    val repositoryName = Resource.getSpec().getConfig().get("repository_name").asText()

    log.info("[create-repository-github] rendering template with repository name $repositoryName")

    val renderedTemplate: String = TemplateApi.renderTemplateAsString(
        mapOf("repositoryName" to repositoryName),
        templateDirPath,
        Context,
        "repository-provider-resource.yaml.jte")

    println(renderedTemplate)


    // apply resource
    PlatformResourceAPI.apply(renderedTemplate, "platformspec")

}