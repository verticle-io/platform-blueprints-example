apiVersion: scaffolder.backstage.io/v1beta3
kind: Template
# some metadata about the template itself
metadata:
  name: platformspec-create-namespace
  title: PlatformSpec Create Namespace
  description: creates a new cluster namespace via the PlatformControlPlane
spec:
  owner: backstage/techdocs-core
  type: service

  # these are the steps which are rendered in the frontend with the form input
  parameters:
    - title: Namespace Configuration
      required:
        - name
      properties:
        name:
          title: Namespace name
          type: string
          description: the namespace
          ui:autofocus: true
          ui:options:
            rows: 5

  # here's the steps that are executed in series in the scaffolder backend
  steps:

    - id: backstage_request
      name: backstage request
      action: http:backstage:request
      input:
        method: 'POST'
        path: '/proxy/platformspec/action/backstage?action=create-namespace&t=${"$"}{{context.task.id}}'
        headers:
          content-type: 'application/json'
        body:
          apiVersion: sdk.platformspec.io/v1alpha1
          kind: PortalActionResource
          metadata:
            name: backstage-create-namespace-${"$"}{{context.task.id}}
            namespace: platformspec
            labels:
              backstage-taskid: ${"$"}{{context.task.id}}
          spec:
            actionName: create-namespace
            description: Creates a new namespace
            labelSelector: category=portal,engine=kubernetes,action=create-namespace
            portalRef:
              apiVersion: core.platformspec.io/v1alpha1
              kind: Portal
              name: backstage
              namespace: platformspec
            config:
              name: ${"$"}{{ parameters.name }}

  output:
    getResponse: '{{ steps.backstage_request.output.body }}'
    getCode: '{{ steps.backstage_request.output.code }}'
    getHeaders: '{{ steps.backstage_request.output.headers }}'
