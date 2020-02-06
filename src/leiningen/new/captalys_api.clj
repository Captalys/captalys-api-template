(ns leiningen.new.captalys-api
  (:require [leiningen.new.templates :refer [multi-segment sanitize-ns renderer name-to-path ->files]]
            [leiningen.core.main :as main]))

(def render (renderer "captalys-api"))

(defn captalys-api
  "FIXME: write documentation"
  [name]
  (let [data {:name name
              :namespace (multi-segment (sanitize-ns name))
              :sanitized (name-to-path name)}]
    (main/info "Generating fresh 'lein new' captalys-api project.")
    (->files data
             ["src/{{sanitized}}/core.clj" (render "core.clj" data)]
             ["src/{{sanitized}}/migrations.clj" (render "migrations.clj" data)]
             ["src/{{sanitized}}/server.clj" (render "server.clj" data)]
             ["src/{{sanitized}}/services/config.clj" (render "config.clj" data)]
             ["src/{{sanitized}}/services/database.clj" (render "database.clj" data)]
             ["src/{{sanitized}}/services/web.clj" (render "web.clj" data)]
             ["test/{{sanitized}}/core_test.clj" (render "core_test.clj" data)]
             ["dev/user.clj" (render "user.clj" data)]
             ["dev/database/db-entrypoint.sh" (render "db-entrypoint.sh" data)]
             ["resources/config.edn" (render "config.edn" data)]
             ["resources/migrations/20200128012252-first-tables-in-the-system.down.sql" (render "20200128012252-first-tables-in-the-system.down.sql" data)]
             ["resources/migrations/20200128012252-first-tables-in-the-system.up.sql" (render "20200128012252-first-tables-in-the-system.up.sql" data)]
             ["README.md" (render "README.md" data)]
             ["CHANGELOG.md" (render "CHANGELOG.md" data)]
             ["LICENSE" (render "LICENSE" data)]
             ["CONTRIBUTORS.md" (render "CONTRIBUTORS.md" data)]
             ["docker-compose.yml" (render "docker-compose.yml" data)]
             ["DockerfileDev" (render "DockerfileDev" data)]
             ["entrypoint-dev.sh" (render "entrypoint-dev.sh" data)]
             ["project.clj" (render "project.clj" data)]
             )))
