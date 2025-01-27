scalaVersion := "3.3.4" // A Long Term Support version.

enablePlugins(ScalaNativePlugin)

// set to Debug for compilation details (Info is default)
logLevel := Level.Info

// import to add Scala Native options
import scala.scalanative.build._

// defaults set with common options shown
nativeConfig ~= { c =>
  c.withLTO(LTO.none) // thin
    .withMode(Mode.debug) // releaseFast
    .withGC(GC.immix) // commix
}

// generate bindings from c header
// libclang-17.so.17: cannot open shared object file
// usage: snbindgen_native --header rkllm.h --package librkllm
enablePlugins(ScalaNativePlugin, BindgenPlugin)
import bindgen.interface.Binding

val rkllmHeader = "rkllm.h"
bindgenBindings := Seq(
  Binding(
    (Compile / resourceDirectory).value / rkllmHeader,
    "librkllm" // library package name
  )
  // .addCImport(rkllmHeader) /* 3 */
  // .build
)
