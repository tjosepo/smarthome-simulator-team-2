import React from 'react';
import './style.scss';

function OutputConsole() {
  return (
    <div className="OutputConsole">
      <code>
        10:07:59: Executing task 'Main.main()'...\n
        \n
        Task :compileJava\n
        Task :processResources NO-SOURCE\n
        Task :classes\n
        \n
        Task :Main.main()\n
        [main] INFO io.javalin.Javalin -\n
        __                      __ _\n
        / /____ _ _   __ ____ _ / /(_)____\n
        __  / // __ `/| | / // __ `// // // __ \\n
        / /_/ // /_/ / | |/ // /_/ // // // / / /\n
        \____/ \__,_/  |___/ \__,_//_//_//_/ /_/\n
        \n
        https://javalin.io/documentation\n
        \n
        [main] INFO org.eclipse.jetty.util.log - Logging initialized @243ms to org.eclipse.jetty.util.log.Slf4jLog\n
        [main] INFO io.javalin.Javalin - Starting Javalin ...\n
        [main] INFO io.javalin.Javalin - Listening on http://localhost:7000/\n
        [main] INFO io.javalin.Javalin - Javalin started in 287ms \o/\n
      </code>
    </div>
  );
}

export default OutputConsole;
