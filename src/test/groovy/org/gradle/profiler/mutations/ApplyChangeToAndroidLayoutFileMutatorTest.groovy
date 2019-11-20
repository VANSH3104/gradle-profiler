package org.gradle.profiler.mutations

class ApplyChangeToAndroidLayoutFileMutatorTest extends AbstractMutatorTest {

    def "adds new view at bottom of top level layout"() {
        def sourceFile = tmpDir.newFile("strings.xml")
        sourceFile.text = "<LinearLayout></LinearLayout>"
        def mutator = new ApplyChangeToAndroidLayoutFileMutator(sourceFile)

        when:
        mutator.beforeBuild(buildContext)

        then:
        sourceFile.text == """\
        <LinearLayout><View 
            android:id="@+id/viewUNIQUE_ID"
            android:visibility="gone"
            android:layout_width="5dp"
            android:layout_height="5dp"/>

        </LinearLayout>""".stripIndent()
        1 * buildContext.uniqueBuildId >> "UNIQUE_ID"
        0 * _
    }


    def "adds new view at bottom of top level db layout"() {
        def sourceFile = tmpDir.newFile("strings.xml")
        sourceFile.text = '<layout><LinearLayout></LinearLayout></layout>'
        def mutator = new ApplyChangeToAndroidLayoutFileMutator(sourceFile)

        when:
        mutator.beforeBuild(buildContext)

        then:
        sourceFile.text == """\
        <layout><LinearLayout><View 
            android:id="@+id/viewUNIQUE_ID"
            android:visibility="gone"
            android:layout_width="5dp"
            android:layout_height="5dp"/>

        </LinearLayout></layout>""".stripIndent()
        1 * buildContext.uniqueBuildId >> "UNIQUE_ID"
        0 * _
    }

    def "reverts changes when nothing has been applied"() {
        def sourceFile = tmpDir.newFile("strings.xml")
        sourceFile.text = "<LinearLayout></LinearLayout>"
        def mutator = new ApplyChangeToAndroidLayoutFileMutator(sourceFile)

        when:
        mutator.afterScenario(scenarioContext)

        then:
        sourceFile.text == "<LinearLayout></LinearLayout>"
    }

    def "reverts changes when changes have been applied"() {
        def sourceFile = tmpDir.newFile("strings.xml")
        sourceFile.text = "<LinearLayout></LinearLayout>"
        def mutator = new ApplyChangeToAndroidLayoutFileMutator(sourceFile)

        when:
        mutator.beforeBuild(buildContext)
        mutator.afterScenario(scenarioContext)

        then:
        sourceFile.text == "<LinearLayout></LinearLayout>"
    }
}
