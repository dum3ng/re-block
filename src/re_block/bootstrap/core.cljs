(ns re-block.bootstrap.core
  (:require [re-block.bootstrap.badge :as badge]
            [re-block.bootstrap.breadcrumb :as breadcrumb]
            [re-block.bootstrap.button :as button]
            [re-block.bootstrap.card :as card]
            [re-block.bootstrap.grid :as grid]

            [re-block.bootstrap.icon :as icon]
            [re-block.bootstrap.list-group :as list-group ]
            [re-block.bootstrap.text :as text]
            ))

(def badge badge/badge)

(def breadcrumb breadcrumb/breadcrumb)
(def breadcrumb-item breadcrumb/breadcrumb-item)

(def button button/button)
(def button-group button/button-group)
(def button-toolbar button/button-toolbar)

(def card card/card)
(def card-body card/card-body)
(def card-columns card/card-columns)
(def card-group card/card-group)
(def card-header card/card-header)
(def card-img card/card-img)
(def card-img-overlay card/card-img-overlay)
(def card-title card/card-title)
(def card-text card/card-text)
(def card-deck card/card-deck)
(def card-link card/card-link)
(def card-subtitle card/card-subtitle)
(def card-footer card/card-footer)

(def grid grid/grid)
(def row grid/row)
(def col grid/col)

(def icon icon/icon)


(def list-group list-group/list-group)
(def list-group-item list-group/list-group-item)
(def list-group-item-text list-group/list-group-item-text)
(def list-group-item-heading list-group/list-group-item-heading)

(def text text/text)
