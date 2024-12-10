import io.fabric8.kubernetes.api.model.ObjectReference
import io.platformspec.crd.provider.spec.Categories
import io.verticle.oss.platformapi.kinds.provider.api.cicd.Environment
import io.verticle.oss.platformapi.kinds.provider.spi.*
import io.verticle.oss.platformscript.api.*

val prj = "acme"

fun bootstrapProjectWorkflow(){

    log.info("starting script")

    val credentials = CredentialApi.getCredential("aws-creds")
    val secrets     = ProviderApi.get(Categories.secrets, "hashivault") as? SecretsProvider
    val cicd        = ProviderApi.get(Categories.cicd, "argocd") as? CicdProvider
    val iaas        = ProviderApi.get(Categories.iaas, "azure") as? IaasProvider

    assert(credentials != null)
    log.info("[X] Credentials available")

    assert(secrets != null)
    log.info("[X] Secrets (hashivault) available")

    assert(cicd != null)
    log.info("[X] CICD (argo) available")

    assert(iaas != null)
    log.info("[X] IaaS (azure) available")

    // load all projects (universal: environments) available in ArgoCD
    cicd?.getAllEnvironments(null)?.forEach {
        log.info("found CICD env " + it.name)
    }

    // create a new default project
    cicd?.createEnvironment(null, Environment.builder()
        .name(prj)
        .description("the ACME project")
        .namespace("default")
        .build())

}

/**
 * - create a new EntraID Application
 * - store the secrets in HashiVault
 * - configure ArgoCD instance
 */
fun bootstrapSSO(){

}
