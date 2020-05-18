# captalys-api

## To maintainer of this project

In order to distribute the template, you need to run `lein install`
and watchout to not provide a SNAPSHOT version because it seems like
leiningen has a bug to deal with it.

After that, run `lein deploy clojars`.

It might be necessary to add the plugin to your
`~/.lein/profile.clj` file depending on your Leiningen
version.

`{:user {:plugins [[captalys-api-template "0.1.5"]]}}`

For leiningen 2.x it will download the jar from Clojars if
not found.


## To users of this template

### Usage

`lein new captalys-api <new-project-name>`



## License

Copyright © 2020 Wanderson Ferreira

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.

This Source Code may also be made available under the following Secondary
Licenses when the conditions for such availability set forth in the Eclipse
Public License, v. 2.0 are satisfied: GNU General Public License as published by
the Free Software Foundation, either version 2 of the License, or (at your
option) any later version, with the GNU Classpath Exception which is available
at https://www.gnu.org/software/classpath/license.html.
