(ns com.example.ui
  (:require 
    [com.example.mutations :as mut]
    [com.fulcrologic.fulcro.algorithms.merge :as merge]
    [com.fulcrologic.fulcro.algorithms.tempid :as tempid]
    [com.fulcrologic.fulcro.algorithms.data-targeting :as targeting]
    [com.fulcrologic.fulcro.algorithms.normalized-state :as norm]
    [com.fulcrologic.fulcro.components :as comp :refer [defsc transact!]]
    [com.fulcrologic.fulcro.raw.components :as rc]
    [com.fulcrologic.fulcro.data-fetch :as df]    
    [com.fulcrologic.fulcro.dom :as dom :refer [button div form h1 h2 h3 input label li ol p ul]]))

(defsc CountryCard [this {:country/keys [name population capital region] }]
  {:query [:country/name :country/population :country/capital :country/region]
   :initial-state (fn [{:country/keys [name population capital region]}]
                    [{:country/name name :country/population population :country/capital capital :country/region region}])}
  (div (h1 name) (p "Population:" population) (p "Capital:" capital) (p "Region:" region))
  )

(def ui-contry-card (comp/factory CountryCard {:keyfn :country/name}))

(defsc Root [this {:keys [countries]}]
  {:query [{:countries (comp/get-query CountryCard)}]
   :initial-state (fn [_]
                    {:countries (comp/get-initial-state CountryCard {:country/name "uk" :country/population 3 :country/capital "bristol" :country/region "Europe"})})}
  (div "Where in the world"
       (map ui-contry-card countries)))
        