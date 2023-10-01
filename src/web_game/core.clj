(ns web-game.core
  (:gen-class)
   (:require 
    [org.httpkit.server :as hk-server] 
    [compojure.core :refer :all]
    [compojure.route :as route]
    [ring.middleware.not-modified :refer [wrap-not-modified]]
    [ring.middleware.content-type :refer [wrap-content-type]]
    [ring.util.response :refer [file-response
                                resource-response
                                content-type]] 
    [ring.middleware.resource :refer [wrap-resource]])
  (:import (java.util Date)))

(defn handleEntry [req]
  (println "received request on entry: " req)
  (let [r (-> (resource-response "public/index.html")
              (content-type "text/html")
              )]
    (println "r is : " r)
    r))

(defn handleIndex [req]
  (println "received req" req) 
   "<h1>Index</h1>")

(defroutes webRoutes
  (GET "/index" [] handleIndex)
  (GET "/" [] handleEntry)
  ;(route/files "/")
  (route/not-found "<h1>Page not found</h1>"))

(defn addMiddleWare [routes]
  (println "adding middleware")
  (-> routes
      (wrap-resource "public")
      ;(wrap-resource "public/assets")
      ;(wrap-content-type "text/html")
      (wrap-content-type )
      (wrap-not-modified)
      ))

(defn get-now [] (Date.))

(def serverApp
  (addMiddleWare webRoutes))

(defn start-my-server [app port] 
  (hk-server/run-server app {:port port}))

(defn startServerApp []
  (let [p 3000]
     (println "Starting server on port: " p)
     (-> webRoutes
         (addMiddleWare)
         (start-my-server p))))



(defn -main
  [& args]
  (startServerApp))


