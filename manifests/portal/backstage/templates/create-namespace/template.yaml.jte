apiVersion: scaffolder.backstage.io/v1beta3
kind: Template
# some metadata about the template itself
metadata:
  name: taps-saas-namespace
  title: TAPS SaaS Project Workspace
  description: prepares a new workspace including ArgoCD project + deployment, Kubernetes namespaces
spec:
  owner: backstage/techdocs-core
  type: service

  # these are the steps which are rendered in the frontend with the form input
  parameters:
    - title: Project Configuration
      required:
        - name
      properties:
        name:
          title: Workspace name
          type: string
          description: the workspace or domain name on TCP
          ui:autofocus: true
          ui:options:
            rows: 5
        argo_project:
          title: TCP Project (ArgoCD)
          description: the ArgoCD project that the new workspace should be part of
          type: array
          items:
            type: string
            enum:
              - tcp-gateway-us
              - tcp-saas-us
          uniqueItems: true
          ui:widget: select
        region:
          title: TCP region
          type: array
          items:
            type: string
            enum:
              - eastus
              - westus
          uniqueItems: true
          ui:widget: checkboxes
        customer:
          title: Customer name
          type: string
          description: the customer name
          ui:autofocus: true
          ui:options:
            rows: 5

        #        namespace_scheme:
        #          title: namespaces kubernetes
        #          type: array
        #          items:
        #            type: string
        #            enum:
        #              - stable-staging-unstable
        #              - production-staging-development
        #          description: the namespace setup flavor
        #          ui:autofocus: true
        #          ui:widget: select

        prefix:
          title: Project Prefix
          type: string
          description: 3 or 4 letter project prefix, e.g. SCY
          ui:options:
            rows: 5
    - title: Choose a deployment repo location
      required:
        - repoUrl
      properties:
        repoUrl:
          title: Deployment Repository Location (for ArgoCD)
          type: string
          ui:field: RepoUrlPicker
          ui:options:
            allowedHosts:
              - github.com
    - title: Choose a location
      required:
        - repoUrl
      properties:
        repoUrl:
          title: Repository Location
          type: string
          ui:field: RepoUrlPicker
          ui:options:
            allowedHosts:
              - github.com

  # here's the steps that are executed in series in the scaffolder backend
  steps:
    - id: fetch-base
      name: Fetch Base
      action: fetch:template
      input:
        url: ./content
        values:
          name: ${"$"}{{ parameters.name }}

    - id: fetch-docs
      name: Fetch Docs
      action: fetch:plain
      input:
        targetPath: ./community
        url: https://github.com/backstage/community/tree/main/backstage-community-sessions

    - id: publish
      name: Publish
      action: publish:github
      input:
        allowedHosts: ['github.com']
        description: This is ${"$"}{{ parameters.name }}
        repoUrl: ${"$"}{{ parameters.repoUrl }}

    - id: register
      name: Register
      action: catalog:register
      input:
        repoContentsUrl: ${"$"}{{ steps['publish'].output.repoContentsUrl }}
        #repoContentsUrl: 'https://github.com/jenssaade/backstagetest'

        catalogInfoPath: '/catalog-info.yaml'