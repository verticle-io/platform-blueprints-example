import io.fabric8.kubernetes.api.model.ObjectReference
import io.platformspec.crd.provider.spec.Categories
import io.verticle.oss.platformapi.kinds.provider.api.cicd.Environment
import io.verticle.oss.platformapi.kinds.provider.spi.*
import io.verticle.oss.platformscript.api.*
import io.platformspec.crd.provider.Spec
import io.verticle.oss.platformapi.serviceprovider.Context
import io.cloudevents.core.v1.*
import io.verticle.oss.platformsdk.core.events.*
import java.net.URI
import java.util.UUID

val prj = "acme"

fun bootstrapProjectWorkflow(){

    log.info("starting script")

    Platform.emitEvent(CloudEventBuilder()
        .withType("Normal")
        .withId(UUID.randomUUID().toString())
        .withSource(URI.create("https://platformspec.io"))
        .withSubject(Resource.getMetadata().getName())
        .withData(EventData.builder()
            .msg("bootstrapProjectWorkflow() ")
            .action("reconcile")
            .reason("processing")
            .hasMetadata(Resource)
            .build())
        .build(), false)

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
    cicd?.createEnvironment(Resource?.getSpec() as Spec , Context as Context,
        Environment.builder()
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
