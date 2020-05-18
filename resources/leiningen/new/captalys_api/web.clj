(ns {{name}}.services.web
  (:require [clojure.spec.alpha :as s]
            [mount.core :as mount]
            [muuntaja.core :as m]
            [reitit.coercion.spec]
            [reitit.ring :as ring]
            [reitit.ring.coercion :as coercion]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.middleware.parameters :as parameters]
            [reitit.ring.middleware.exception :as exception]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.dev.pretty :as pretty]
            [org.httpkit.server :as http]
            [{{name}}.services.config :refer [config]]))

(s/def ::x float?)
(s/def ::y float?)
(s/def ::math-request (s/keys :req-un [::x ::y]))

(defn handle-default-rtx [req]
  {:status 200
   :body {:total 10}})

(def app
  (ring/ring-handler
   (ring/router
    [["/add" {:post {:summary "adding numbers, because we can"
                     :parameters {:body ::math-request}
                     :handler handle-default-rtx}}]

     ["api/healthcheck" {:tags ["api"]
                         :get {:summary "how health is this system?"
                               :handler (fn [_]
                                          {:status 200
                                           :body {:message "Very lispy!"}})}}]

     ["/swagger.json" {:get {:no-doc true
                             :swagger {:info {:title "{{name}} API"
                                              :description "let's get better at this!"}}
                             :handler (swagger/create-swagger-handler)}}]]
    {:exception pretty/exception
     :data {:muuntaja m/instance
            :coercion reitit.coercion.spec/coercion
            :middleware [;; swagger feature
                         swagger/swagger-feature
                         ;; query-params & form-params
                         parameters/parameters-middleware
                         ;; content negotiation
                         muuntaja/format-negotiate-middleware
                         ;; encoding response body
                         muuntaja/format-response-middleware
                         ;; exception handling
                         exception/exception-middleware
                         ;; decoding request body
                         muuntaja/format-request-middleware
                         ;; coercing response body
                         coercion/coerce-response-middleware
                         ;; coercing request parameters
                         coercion/coerce-request-middleware
                         ]}})
   (ring/routes
    (swagger-ui/create-swagger-ui-handler {:path "/docs"
                                           :config {:validatorUrl nil
                                                    :operationsSorter "alpha"}})
    (ring/create-default-handler))))


(mount/defstate server
  :start (let [port (Integer. (get-in config [:webserver :port]))]
           (http/run-server #'app {:port port :join? false}))
  :stop (server))
