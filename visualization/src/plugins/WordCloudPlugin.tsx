import React from 'react';
import './WordCloudPlugin.css';
import PersonalPagePlugin from '../framework/PersonalPagePlugin';
import Resume from '../framework/Resume';
import AnyChart from 'anychart-react';

// Reference: the Original Word Count Plugin
function WordCloudPlugin(): PersonalPagePlugin {
    const name: string = "WordCloud"

    return {
        getName(): string {
            return name;
        },

        getContent(resume: Resume): JSX.Element {
            const originalWordCountMap = new Map(Object.entries(resume.wordCount))

            let data: any[] = []
            for (const [word, count] of Array.from(originalWordCountMap)) {
                // Filter out non-alphabetic words
                if (!word.match(/^[a-zA-Z]+$/)) {
                    continue
                }
                // Filter out words that are too short (possibly stop words)
                if (word.length <= 3) {
                    continue
                }

                data.push({ x: word, value: count })
            }

            // Sort the data by count
            data = data.sort((a, b) => b.value - a.value)
            // Take the top 50 words
            data = data.slice(0, 50)
            
            return (
                <div className="WordCloudPlugin">
                    <AnyChart
                        type="tagCloud"
                        data={data}
                        width={1000}
                        height={1000}
                        title="Top 50 Words Appeared in the Resume"
                    />
                </div>
            )
        }
    }

}

export default WordCloudPlugin;