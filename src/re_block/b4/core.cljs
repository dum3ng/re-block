(ns re-block.b4.core
  (:require
   [re-block.b4.alert :as alert]
   [re-block.b4.button :as button]
   [re-block.b4.button-group :as bg]
   [re-block.b4.badge :as badge]
   [re-block.b4.breadcrumb :as breadcrumb]
   [re-block.b4.breadcrumb-item :as bi]

   [re-block.b4.close-button :as cb]

   [re-block.b4.nav :as nav]
   
   [re-block.b4.card :as card]))

(def alert alert/alert)

(def button button/button)
(def button-group bg/button-group)

(def badge badge/badge)
(def breadcrumb breadcrumb/breadcrumb)
(def breadcrumb-item bi/breadcrumb-item)

(def close-button cb/close-button)

(def navs nav/navs)


(def card-title card/card-title)
(def card-text card/card-text)
(def card-image card/card-image)
(def card-body card/card-body)
(def card card/card)
