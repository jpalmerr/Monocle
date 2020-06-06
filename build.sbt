name := "Monocle"

version := "0.1"

scalaVersion := "2.13.2"

val monocleVersion = "2.0.0" // depends on cats 2.x
val catsVersion = "2.1.1"
val scalaTestVersion = "3.1.0"

libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %%  "monocle-core"  % monocleVersion,
  "com.github.julien-truffaut" %%  "monocle-macro" % monocleVersion,
  "org.typelevel"              %% "cats-core"      % catsVersion,
  "org.scalactic"              %% "scalactic"      % scalaTestVersion,
  "com.github.julien-truffaut" %%  "monocle-law"   % monocleVersion    % "test",
  "org.scalatest"              %%  "scalatest"      % scalaTestVersion % "test"
)
