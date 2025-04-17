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
import io.verticle.oss.platformapi.kinds.provider.crd.portal.template.PortalTemplateResource

fun prepareTemplate(){

    log.info("starting script")

    // get all clusters
    val clusters = ClusterApi.getClusters()


    // render the template with the clusters
    log.info("rendering template ...")

    val templateDirPath = Paths.get("manifests", "portal", "backstage", "templates", "create-namespace")

    val renderedTemplate: String = TemplateApi.renderTemplateAsString(
        mapOf("clusters" to clusters),
        templateDirPath,
        Context,
        "template.yaml.jte")    

    println(renderedTemplate)


    // resolve resource
    //var resource = PlatformResourceAPI.resolve(renderedTemplate, "platformspec")
    val portalTemplateResource =  Resource as PortalTemplateResource

    portalTemplateResource.getSpec().setTemplate(renderedTemplate)
    //portalTemplateResource.getSpec().setTemplate("test")
    //${{

    // apply resource
    PlatformResourceAPI.apply(portalTemplateResource, "platformspec")


    // OPTIONALLY STORE IN GIT REPO
    //val res:RepositoryProviderResource = Context.findReference(Resource.spec.repositoryProviderRef) as RepositoryProviderResource
    //log.info("res $res")

    //val gitRepo = PlatformGitApi.gitGetRepositoryFor(res, Context)

    // create template file
    //val localReposPath: Path = PlatformGitApi.getLocalRepositoryPath(gitRepo)

    //PlatformFileApi.cloneDirectoryFromBlueprint(templateDirPath.resolve("content"), gitRepo, Paths.get("content"))
    //PlatformGitApi.gitAddDirectory(Paths.get("content"), gitRepo)

    // add files
    //val filePath: Path = localReposPath.resolve("catalog-info.yaml")

    //log.info("adding template file to repo $filePath")
    //Files.write(filePath, renderedTemplate.toByteArray())

    //PlatformGitApi.gitAddFile("catalog-info.yaml", renderedTemplate, gitRepo)

    // commit them
    //PlatformGitApi.gitCommit("update", gitRepo)

}