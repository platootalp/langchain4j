package dev.langchain4j.store.embedding.weaviate;

import static dev.langchain4j.internal.Utils.randomUUID;

import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.weaviate.WeaviateContainer;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIT;

@Testcontainers
class LocalWeaviateMetadataAtBaseStoreIT extends EmbeddingStoreIT {

    @Container
    static WeaviateContainer weaviate = new WeaviateContainer("semitechnologies/weaviate:latest")
            .withEnv("QUERY_DEFAULTS_LIMIT", "25")
            .withEnv("DEFAULT_VECTORIZER_MODULE", "none")
            .withEnv("CLUSTER_HOSTNAME", "node1");

    private final EmbeddingStore<TextSegment> embeddingStore = WeaviateEmbeddingStore.builder()
            .scheme("http")
            .host(weaviate.getHost())
            .port(weaviate.getFirstMappedPort())
            .objectClass("Test" + randomUUID().replace("-", ""))
            .metadataKeys(LocalWeaviateEmbeddingStoreIT.METADATA_KEYS)
            .metadataFieldName("")
            .build();

    EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();

    @Override
    protected EmbeddingStore<TextSegment> embeddingStore() {
        return embeddingStore;
    }

    @Override
    protected EmbeddingModel embeddingModel() {
        return embeddingModel;
    }

    @Override
    protected void ensureStoreIsEmpty() {
        // TODO fix
    }
}
