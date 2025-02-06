apiVersion: scaffolder.backstage.io/v1beta3
kind: Template
# some metadata about the template itself
metadata:
  name: taps-saas-project-repository
  title: TAPS SaaS Project Repository
  description: prepares a new project repository on GitHub
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




  # here's the steps that are executed in series in the scaffolder backend
  steps:
    - id: fetch-base
      name: Fetch Base
      action: fetch:template
      input:
        url: ./content
        values:
          name: ${"$"}{{ parameters.name }}


    #- id: publish
    #  name: Publish
    #  action: publish:github
    #  input:
    #    allowedHosts: ['github.com']
    #    description: This is ${"$"}{{ parameters.name }}
    #    repoUrl: ${"$"}{{ parameters.repoUrl }}

    #- id: register
    #  name: Register
    #  action: catalog:register
    #  input:
    #    repoContentsUrl: ${"$"}{{ steps['publish'].output.repoContentsUrl }}
    #    #repoContentsUrl: 'https://github.com/jenssaade/backstagetest'
    #    catalogInfoPath: '/catalog-info.yaml'