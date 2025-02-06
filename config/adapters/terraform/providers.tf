locals {
  subscription_id = var.subscription_id
  client_id       = var.client_id
  client_secret   = var.client_secret
  tenant_id       = var.tenant_id
  azuread_use_cli = false
}

terraform {

  required_providers {
    azuread = {
      source = "hashicorp/azuread"
    }
  }
}

provider "azurerm" {
  features {

  }

  subscription_id = local.subscription_id
  client_id       = local.client_id
  client_secret   = local.client_secret
  tenant_id       = local.tenant_id
}

provider "azuread" {
  client_id     = local.client_id
  client_secret = local.client_secret
  tenant_id     = local.tenant_id
  use_cli       = local.azuread_use_cli


}