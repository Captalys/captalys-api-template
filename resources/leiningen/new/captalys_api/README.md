# {{name}}

How Captalys is working in Clojure to build Services APIs to its internal processes.


## General guidelines

1. RESTful APIs, follows some guidelines provided by [WhiteHouse/api-standards](https://github.com/WhiteHouse/api-standards)
2. Documentation and consistency of APIs are very important to us
3. Tests, we would think about what need to be tested and try to use **generative testing** as much as possible
4. Program towards interfaces instead of concrete implementations, `multimethods` and `protocols` are friends

## Usage

```bash
WEBSERVER_PORT=3000 CURRENT_UID=$(id -u):$(id -g) docker-compose up
```

Connect your project to the new created REPL. If you are
using CIDER in Emacs, just `M-x cider-connect-clj` or `C-c
M-c` keychord. The host is `localhost` and the port should
be `17020`.

Now, start the webserver with the `(start)` command inside the REPL.
Your API should be on at the `http://localhost:17021/`

You should also manually apply the migrations. This should do the trick:

```clj
(require '[captalys-api.migrations :as m])
(m/apply-migrations)
```

## Emacs users

Please, edit the file `.dir-locals.el` to match the path of
your machine until this project. The CIDER package needs
that to provide you with auto-completions and more features
when you connect to a remote REPL [remote in this case
because we are outside Docker connecting to a REPL inside of
it.]


### Linting

Steps to perform linting correctly in the project.

1. Install [clj-kondo](https://github.com/borkdude/clj-kondo)
2. Create an empty directory in the root of the project called `.clj-kondo`
3. Run the following command in the terminal to teach `clj-kondo` about the project `clj-kondo --lint "$(lein classpath)"`
4. Find about [Editor Integration](https://github.com/borkdude/clj-kondo/blob/master/doc/editor-integration.md)


#### Report about unused vars and functions

As you are using Emacs (congrats!), run the following command in the Eshell.

`clojure -A:carve --opts '{:paths ["src" "test"] :report {:format :text}}'`

If you don't have `clojure` installed in your machine, please do!

After that, you need to create a file called `deps.edn`
inside a folder called `$HOME/.clojure` with the following content:

```clojure
:carve
  {:extra-deps {borkdude/carve {:git/url "https://github.com/borkdude/carve"
                                :sha "8e219572f55485244fdc35ac605d19bc74e9be0e"}}
   :main-opts ["-m" "carve.main"]}
```

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
