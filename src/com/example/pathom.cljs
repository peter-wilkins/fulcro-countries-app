(ns com.example.pathom
  "The Pathom parser that is our (in-browser) backend.
   
   Add your resolvers and 'server-side' mutations here."
  (:require
    [com.wsscode.pathom.core :as pm]
    [com.wsscode.pathom.connect :as pc]
    [promesa.core :as p]))

(pc/defresolver index-explorer
  "This resolver is necessary to make it possible to use 'Load index' in Fulcro Inspect - EQL"
  [env _]
  {::pc/input #{:com.wsscode.pathom.viz.index-explorer/id}
   ::pc/output [:com.wsscode.pathom.viz.index-explorer/index]}
  {:com.wsscode.pathom.viz.index-explorer/index
   (-> (get env ::pc/indexes)
       (update ::pc/index-resolvers #(into {} (map (fn [[k v]] [k (dissoc v ::pc/resolve)])) %))
       (update ::pc/index-mutations #(into {} (map (fn [[k v]] [k (dissoc v ::pc/mutate)])) %)))})

(defn json-get [url]
  (p/let [resp (js/fetch url)
          json (.json resp)]
    (js->clj json :keywordize-keys true)))

(defn all-countries* []
  (->> (json-get "https://restcountries.com/v2/all?fields=name,capital,region,population,flags")
       ))

(comment


  (defonce state (atom nil))
  (-> (all-countries*)
      (.then #(reset! state %)))

  @state

  (->> @state
       (map
         (fn [{:keys [name capital region population flags]}]
           {:country/name name :country/population population :country/capital capital :country/region region :country/flag flags})

         ))
  )

(pc/defresolver all-countries [_]
  {::pc/output [:countries]}
  {:countries (all-countries*)})

(def my-resolvers-and-mutations
  "Add any resolvers you make to this list (and reload to re-create the parser)"
  [index-explorer all-countries])

(defn new-parser
  "Create a new Pathom parser with the necessary settings"
  []
  (pm/parallel-parser
    {::p/env {::p/reader [pm/map-reader
                          pc/parallel-reader
                          pc/open-ident-reader]
              ::pc/mutation-join-globals [:tempids]}
     ::p/mutate pc/mutate-async
     ::p/plugins [(pc/connect-plugin {::pc/register my-resolvers-and-mutations})
                  pm/error-handler-plugin
                  pm/request-cache-plugin
                  (pm/post-process-parser-plugin pm/elide-not-found)]}))
