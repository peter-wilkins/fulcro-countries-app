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
    [com.fulcrologic.fulcro.dom :as dom :refer [button div form h1 h2 h3 input label li ol p ul img]]))

(defsc CountryCard [this {:country/keys [name population capital region flag] }]
  {:query [:country/name :country/population :country/capital :country/region :country/flag]
   :initial-state (fn [{:country/keys [name population capital region]}]
                    [{:country/name name :country/population population :country/capital capital :country/region region}])}
  (div
    (img {:src flag})
    (h1 name)
    (p "Population:" population)
    (p "Capital:" capital)
    (p "Region:" region))
  )

(def ui-contry-card (comp/factory CountryCard {:keyfn :country/name}))

(defsc Root [this {:keys [countries]}]
  {:query [{:countries (comp/get-query CountryCard)}]
   :initial-state (fn [_]
                    {:countries [#:country{:name "United Kingdom of Great Britain and Northern Ireland",
                                           :population 67215293,
                                           :capital "London",
                                           :region "Europe",
                                           :flag "https://flagcdn.com/w320/gb.png"}
                                 #:country{:name "United States of America",
                                           :population 329484123,
                                           :capital "Washington, D.C.",
                                           :region "Americas",
                                           :flag "https://flagcdn.com/w320/us.png"}
                                 #:country{:name "Uruguay",
                                           :population 3473727,
                                           :capital "Montevideo",
                                           :region "Americas",
                                           :flag "https://flagcdn.com/w320/uy.png"}
                                 #:country{:name "Uzbekistan",
                                           :population 34232050,
                                           :capital "Tashkent",
                                           :region "Asia",
                                           :flag "https://flagcdn.com/w320/uz.png"}
                                 #:country{:name "Vanuatu",
                                           :population 307150,
                                           :capital "Port Vila",
                                           :region "Oceania",
                                           :flag "https://flagcdn.com/w320/vu.png"}
                                 #:country{:name "Venezuela (Bolivarian Republic of)",
                                           :population 28435943,
                                           :capital "Caracas",
                                           :region "Americas",
                                           :flag "https://flagcdn.com/w320/ve.png"}
                                 #:country{:name "Vietnam",
                                           :population 97338583,
                                           :capital "Hanoi",
                                           :region "Asia",
                                           :flag "https://flagcdn.com/w320/vn.png"}
                                 #:country{:name "Wallis and Futuna",
                                           :population 11750,
                                           :capital "Mata-Utu",
                                           :region "Oceania",
                                           :flag "https://flagcdn.com/w320/wf.png"}
                                 #:country{:name "Western Sahara",
                                           :population 510713,
                                           :capital "El Aaiún",
                                           :region "Africa",
                                           :flag "https://flagcdn.com/w320/eh.png"}
                                 #:country{:name "Yemen",
                                           :population 29825968,
                                           :capital "Sana'a",
                                           :region "Asia",
                                           :flag "https://flagcdn.com/w320/ye.png"}
                                 #:country{:name "Zambia",
                                           :population 18383956,
                                           :capital "Lusaka",
                                           :region "Africa",
                                           :flag "https://flagcdn.com/w320/zm.png"}
                                 #:country{:name "Zimbabwe",
                                           :population 14862927,
                                           :capital "Harare",
                                           :region "Africa",
                                           :flag "https://flagcdn.com/w320/zw.png"}]
})}
  (div "Where in the world"
       (map ui-contry-card countries)))

