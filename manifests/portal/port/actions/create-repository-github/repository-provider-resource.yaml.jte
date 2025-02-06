@param String repositoryName
---
apiVersion: sdk.platformspec.io/v1alpha1
kind: RepositoryProviderResource
metadata:
  name: repository-${repositoryName}
  namespace: platformspec
spec:
  providerRef:
    kind: provider
    name: github
    namespace: platformspec
  description: a simple sync setup
  repositoryOwner: jenssaade
  repositoryName: ${repositoryName}
  credentialRef:
    kind: Credential
    name: github-token
    namespace: platformspec
  providerResourceConfig: {}
