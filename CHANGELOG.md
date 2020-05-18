# Change Log
All notable changes to this project will be documented in this file. This change log follows the conventions of [keepachangelog.com](http://keepachangelog.com/).

## [0.1.5] - 2020-05-18
### Added
- Explicit functions to mount in the `server` file.
- Endpoint for `api/healthcheck`

### Changed
- The port number in the `config.edn` file from 4000 to 3000.

## [0.1.4] - 2020-05-18
### Added
- Dependencies to `hugsql`, `hikari-cp`, `clj-http` and `http-responses`
- Namespace for databases already have implemented connection-pool and support to JSONB

### Removed
- explicit `metosin/spec-tools` dependency. Reitit already updated their dependencies.

### Fixed
- Fixed missing {{name}} of the client project in `project.clj`

## [0.1.3] - 2020-02-06
### Added
- Files for the new template.
